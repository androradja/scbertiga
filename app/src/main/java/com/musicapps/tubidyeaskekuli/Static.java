package com.musicapps.tubidyeaskekuli;



import java.util.ArrayList;
import java.util.List;

public class Static {
   public static List<SongModel> listtrending = new ArrayList<>();
   public static List<SongModel> listpopular = new ArrayList<>();


   public static String BASEURL="http://soundcloud.fando.id/";
   public static String SEARCH="http://soundcloud.fando.id/search.php?q=";
   public static String GENRE="http://soundcloud.fando.id/genre.php?genre=";
   public static String TOP="http://soundcloud.fando.id/top.php";
   public static String TRENDING="http://soundcloud.fando.id/trending.php";

   public static String getSEARCH(String q){
      return SEARCH+q;
   }

   public static String getGenreSong(String genre){
      return GENRE+genre;
   }


   public static  String MAINHOME="MAINHOME";
   public static  String HOME="HOME";
   public static  String PLAYER="PLAYER";
   public static  String LIRIK="LIRIK";
   public static  String EQ="EQ";
   public static String LOCALINTENTFILTER="musicplayer";
   public static String ACTIONNAME="actname";
   public static String INTENTFILTERNOTIF="musicfando";
   public static String MUSICCONTROLL="musiccontrooll";
   public static String MUSICSTATE="musicstate";


   public static final String ACTION_PREVIUOS = "prev";
   public static final String ACTION_PREPARE = "prepare";
   public static final String ACTION_START = "play";
   public static final String ACTION_STOP = "stop";
   public static final String ACTION_RESUME = "resume";
   public static final String ACTION_PAUSE = "pause";
   public static final String ACTION_NEXT = "next";
   public static final String ACTION_SET_DURATION = "duration";



   public static final String DOWNLOAD_PROGRESS="dprogress";
   public static final String DOWNLOAD_START="dstart";



   //static button type

   public static final String PLAY_BUTTON="playbutton";
   public static final String NEXT_BUTTON="nextbutton";
   public static final String PREV_BUTTON="prevbutton";
   public static final String SEEK_BUTTON="seekbutton";
   public static final String TIMER_BUTTON="timerbutton";
   public static final String CLOSE_BUTTON="closebutton";
   public static final String NO_BUTTON="nobutton";
   public static final String PLAY_CURRENT_VIDEO="playcurrentvid";
   public static final String PLAY_CURRENT_MUSIC="playcurrentmusic";
   public static final String STOP_ALLMUSIC="stopalmusic";
   public static final String STOP_SERVICES="stopservices";
   public static final String SAVE_DURATION="savedura";





}
