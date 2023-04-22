package top.yonyong.vertx.demo;

import io.vertx.core.Vertx;
import top.yonyong.vertx.demo.http.MainVerticle;

public class Application {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }
}
