package com.mrhi2020.ex58notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {

        //Notification(알림) 관리자 객체 소환
        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //알림(Notification)객체를 만들어주는 건축가 객체 생성
        NotificationCompat.Builder builder= null;

        //오레오버전(api 26) 부터 '알림채널'이라는 것이 생겼음
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //'알림채널' 객체 생성
            NotificationChannel channel= new NotificationChannel("ch01","channel #1", NotificationManager.IMPORTANCE_HIGH);

            //사운드 작업
            Uri uri= Uri.parse("android.resource://"+ getPackageName() +"/"+R.raw.s_goodjob );
            channel.setSound(uri, new AudioAttributes.Builder().build());


            notificationManager.createNotificationChannel(channel);

            builder= new NotificationCompat.Builder(this, "ch01");

        }else{
            builder= new NotificationCompat.Builder(this, null);
        }

        //만들어진 건축가에게 Notification의 모양을 설정
        builder.setSmallIcon(R.drawable.ic_stat_name);//상태표시줄에 보이는 아이콘지정

        //확장상태바[상태표시줄을 그래그하여 아래로 내리면 보이는 알림창]
        //그 곳에 보이는 설정들
        builder.setContentTitle("문자왔숑~~~");
        builder.setContentText("알림메세지입니다. 주의하세요.");
        builder.setSubText("sub text");

        //res폴더 창고관리자 객체 소환
        Resources res= getResources();
        Bitmap bm= BitmapFactory.decodeResource(res, R.drawable.moana01);
        builder.setLargeIcon(bm);


        //확장상태바에 큰이미지 스타일 적용
        NotificationCompat.BigPictureStyle bigPictureStyle= new NotificationCompat.BigPictureStyle(builder);
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(res, R.drawable.moana02));

        //여러스타일객체들.
        //Notification.BigTextStyle
        //Notification.InboxStyle
        //Notification.MediaStyle

        builder.setProgress(100, 50, true);//3번째 파라미터 : 상태표시줄에 게이지가 무한루프 여부

        //진동 [ AndroidManfest.xml에서 퍼미션 추가 - 동적퍼미션을 필요없음]
        builder.setVibrate(new long[]{0, 2000, 1000, 3000});//0초대기 2초진동, 1초대기 3초진동

        //사운드작업은 이제 NotificationChannel객체 에게
        //Uri uri= RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);

        //Uri uri= Uri.parse("android.resource://"+ getPackageName() +"/"+R.raw.s_goodjob );
        //builder.setSound(uri);


        //확장상태바의 알림을 클릭했을때 새로운 액티비티로 이동
        Intent intent= new Intent(this, SecondActivity.class);
        //보류중인 인텐트 생성
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //알림 확인했을때 자동으로 알림취소 (setContentIntent()했을때만 가능)
        builder.setAutoCancel(true);


        //알림객체 생성
        Notification notification= builder.build();

        //알림 관리자에게 알림을 보이도록 공지!!
        notificationManager.notify(1, notification);



    }
}
