package top.yonyong.vertx.demo.verticles.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

public class ServiceVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    EventBus eb = vertx.eventBus();

    eb.consumer(this.getClass().getName(), this::handleEventBus);

    System.out.println("Receiver ready!");
  }

  private <T> void handleEventBus(Message<T> tMessage) {
    System.out.println("接收到消息：" + tMessage.body());
    tMessage.reply("accept:" + tMessage.body());
  }

  public static void main(String[] args) {
    System.out.println(ServiceVerticle.class.getCanonicalName());
  }
}
