package com.example.startlight.memoryAlbum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemoryAlbumScheduleService {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final MemoryAlbumFlaskService memoryAlbumFlaskService;

    private Long trainingPetId;

    public void createAlbum(LocalDateTime albumStartedTime, Long petId) {
        trainingPetId = petId;

        // ✅ 1. 처음에는 즉시 실행
        executor.execute(this::executeTask);
        // ✅ 2. 다음 실행까지 남은 시간 계산
        long initialDelay = getInitialDelay(albumStartedTime);
        //long period = TimeUnit.DAYS.toMillis(7); // 일주일 주기 설정
        long period = TimeUnit.MINUTES.toMillis(1);

        System.out.println("✅ First execution immediately, next run in: " + initialDelay + " ms");

        // ✅ 3. 일주일마다 반복 실행 설정
        executor.scheduleAtFixedRate(
                this::executeTask, // 실행할 작업
                initialDelay,
                period,
                TimeUnit.MILLISECONDS
        );
    }

    // ✅ 처음 실행까지의 남은 시간 계산
    private long getInitialDelay(LocalDateTime albumStartedTime) {
        LocalDateTime now = LocalDateTime.now();

        // 만약 입력받은 시간이 현재 시간보다 이전이라면 다음 주기로 설정
        if (albumStartedTime.isBefore(now)) {
            //albumStartedTime = albumStartedTime.plusWeeks(1); // 다음 주기로 설정
            albumStartedTime = albumStartedTime.plusMinutes(1);
        }

        // albumStartedTime → 밀리초로 변환 후 현재 시간과 차이 계산
        return albumStartedTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                - System.currentTimeMillis();
    }

    // ✅ 실행할 작업 함수
    private void executeTask() {
        System.out.println("📅 Task executed at: " + LocalDateTime.now());
        runMyTask();
    }

    // ✅ 실행할 실제 작업 함수
    private void runMyTask() {
        System.out.println("🚀 Task is running...");
        // 실행할 로직 작성
        memoryAlbumFlaskService.generateMemoryAlbum(trainingPetId);
    }

    // ✅ 작업 종료 (필요 시 호출)
    public void stopScheduler() {
        if (!executor.isShutdown()) {
            System.out.println("🛑 Stopping scheduler...");
            executor.shutdown();
        }
    }
}
