package com.Arriety.games;

import AururaFactory.AlexDConfig;
import com.girlkun.models.player.Player;
import com.girlkun.server.Client;
import com.girlkun.services.Service;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LuckyNumberGameManager {
    private static LuckyNumberGameManager instance = null;
    private boolean isStartGame = false;
    private Disposable gameDisposable;

    private int currentCountDown = 0;

    private Map<Integer, List<LuckyNumber>> luckyNumberMaps = new Hashtable<>();
    private Map<Integer, Set<Integer>> playerMap = new Hashtable<>();

    private Set<Integer> playerIds = new HashSet<>();
    private PublishSubject<Integer> countDownSubject = PublishSubject.create();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<String> lastPlayerGotLuckyNumber = new ArrayList<>();

    ExecutorService luckyNumberPool = Executors.newFixedThreadPool(3);

    @Getter
    private int previousLuckyNumber = -1;

    private LuckyNumberGameManager() {
        Disposable subscribe = countDownSubject
                .observeOn(Schedulers.from(luckyNumberPool))
                .subscribe(integer -> {
            if(integer < 8 && integer % 2 == 0 && integer > 0) {
                playerIds.forEach(playerId -> {
                    Player player = Client.gI().getPlayer(playerId);
                    String notification = String.format("THÔNG BÁO CON SỐ MAY MẮN\n Thời gian quay số may mắn sẽ diễn ra trong %d giây nữa. Chúc bạn may mắn", integer);
                    //System.out.println(notification);
                    Service.getInstance().sendThongBao(player, notification);
                });
            }
        });
        compositeDisposable.add(subscribe);
    }

    public static synchronized LuckyNumberGameManager getInstance() {
        if (instance == null) {
            instance = new LuckyNumberGameManager();
        }
        return instance;
    }

    public String getBookNumber(int playerId) {
        Set<Integer> listBookNumber = playerMap.get(playerId);
        if(listBookNumber == null) {
            return "Chưa chọn só";
        }
        return listBookNumber.stream().map(String::valueOf).collect(Collectors.joining(","));

    }

    public int getCurrentCountDown() {
        return currentCountDown == 0 ? AlexDConfig.LUCKY_GAME_TOTAL_TIME : currentCountDown;
    }

    public int getTotalReward() {
        int totalGem = 0;
        for (Map.Entry<Integer, List<LuckyNumber>> entry : luckyNumberMaps.entrySet()) {
            totalGem += entry.getValue().stream().mapToInt(LuckyNumber::getQuantity).sum();
        }
        return totalGem * AlexDConfig.LUCKY_GEM_COUNT;
    }

    private int getLuckyNumber() {
        Random r = new Random();
        int low = AlexDConfig.LUCKY_NUMBER_MIN;
        int high = AlexDConfig.LUCKY_NUMBER_MAX;
        return r.nextInt(high-low) + low;
    }

    public void handlePlayerInput(int playerId, int luckyNumber) {
        Player player = Client.gI().getPlayer(playerId);
        if(player == null) {
            return;
        }

        if(player.inventory.gem <= AlexDConfig.LUCKY_GEM_COUNT) {
            Service.getInstance().sendThongBao(player, "Bạn không đủ ngọc để tham gia trò chơi");
            return;
        }

        player.inventory.gem -= AlexDConfig.LUCKY_GEM_COUNT;
        Service.getInstance().sendMoney(player);
        playerIds.add(playerId);
        String content = String.format("Bạn đã chọn %d là số may mắn với %d ngọc\nThời gian quay số sẽ diễn ra %s giây nữa.",
                luckyNumber,
                AlexDConfig.LUCKY_GEM_COUNT,
                getCurrentCountDown()
        );
        Service.getInstance().sendThongBaoOK(player, content);

        Set<Integer> bookLuckyNumber = playerMap.get(playerId);
        if(bookLuckyNumber == null) {
            bookLuckyNumber = new HashSet<>();
            bookLuckyNumber.add(luckyNumber);
            playerMap.put(playerId, bookLuckyNumber);
        } else {
            bookLuckyNumber.add(luckyNumber);
        }

        List<LuckyNumber> listLuckyNumbers = luckyNumberMaps.get(luckyNumber);
        if(listLuckyNumbers == null) {
            listLuckyNumbers = new ArrayList<>();
            listLuckyNumbers.add(new LuckyNumber(playerId, 1));
            luckyNumberMaps.put(luckyNumber, listLuckyNumbers);
        } else {
            LuckyNumber luckyPlayer = listLuckyNumbers.stream().filter(i -> i.getPlayerId() == playerId).findFirst().orElse(null);
            if(luckyPlayer != null) {
                luckyPlayer.setQuantity(luckyPlayer.getQuantity() + 1);
            } else {
                listLuckyNumbers.add(new LuckyNumber(playerId, 1));
            }
        }

        if(!isStartGame) {
            startGame();
        }
    }

    public void handlePlayerInput(Player player, int luckyNumber) {
        int playerId = (int) player.id;
        handlePlayerInput(playerId, luckyNumber);
    }

    public void startGame() {
        stopGame();
        isStartGame = true;
        gameDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.from(luckyNumberPool))
                .subscribe(time -> {
                    onCountDown(AlexDConfig.LUCKY_GAME_TOTAL_TIME - time.intValue());
                    if(time >= AlexDConfig.LUCKY_GAME_TOTAL_TIME) {
                        onGameFinish();
                        stopGame();
                    }
                });
    }

    private void onGameFinish() {
        int luckyNumber = getLuckyNumber();
        previousLuckyNumber = luckyNumber;
        //System.out.println("onGameFinish: " + luckyNumber);
        List<LuckyNumber> luckyPlayers = luckyNumberMaps.get(luckyNumber);

        if(luckyPlayers != null && !luckyPlayers.isEmpty()) {
            int total = luckyPlayers.stream().mapToInt(LuckyNumber::getQuantity).sum();
            luckyPlayers.forEach(player -> {
                Player winPlayer = Client.gI().getPlayer(player.getPlayerId());
                if(winPlayer != null && winPlayer.inventory != null) {
                    playerIds.remove(player.getPlayerId());
                    int plusGem = getTotalReward() * player.getQuantity() / total;
                    winPlayer.inventory.gem += plusGem;
                    String notifyContent = String.format("Xin chúc mừng bạn, bạn là người chiến thắng. Bạn đã được cộng %d ngọc vào tài khoản", plusGem);
                    lastPlayerGotLuckyNumber.add(winPlayer.name);
                    Service.getInstance().sendMoney(winPlayer);
                    Service.getInstance().sendThongBaoOK(winPlayer, notifyContent);
                }
            });
        }

        playerIds.forEach(playerId -> {
            Player player = Client.gI().getPlayer(playerId);
            if(player != null) {
                            String notification = String.format("Xin chia buồn cùng bạn.\nCon số may mắn là:\n%d", luckyNumber);
            Service.getInstance().sendThongBaoOK(player, notification);
            }
        });

        luckyNumberMaps.clear();
        playerIds.clear();
        playerMap.clear();
    }

    private void onCountDown(int time) {
        //System.out.println("onCountDowntime:" + time);
        countDownSubject.onNext(time);
        //System.out.println(getTotalReward());
        currentCountDown = time;
    }

    private void stopGame() {
        isStartGame = false;
        if(gameDisposable != null) {
            gameDisposable.dispose();
            gameDisposable = null;
        }
    }

    public int getTotalPerson() {
        return playerIds.size();
    }
}
