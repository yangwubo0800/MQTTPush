package com.example.wg.mqttpush;

public interface PushConfig {
    String CHANNEL_ID = "channel";
    String CHANNEL_NAME = "channel_push";
    String DEFAULT_ALERT = "alert";
    String DEFAULT_TYPE = "base";

    String TYPE_ERROR = "error";
    String TYPE_MQTT = "mqtt";

    //mqtt
    String host = "tcp://192.168.43.103:61613";
    String userName = "admin";
    String passWord = "123";
    String myTopic = "test/topic";
    String myId = "test";
}
