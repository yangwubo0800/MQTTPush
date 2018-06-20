1、服务端需要先使用Apollo作为代理服务器来发消息，参考此文即可搭建：
https://blog.csdn.net/marrn/article/details/71141122?utm_source=itdadao&utm_medium=referral
2、PC端通过java代码，通过MQTT协议来连接服务器，然后在根据界面动作 ，发布消息；
3、手机端设置相同的连接主题参数，订阅消息。