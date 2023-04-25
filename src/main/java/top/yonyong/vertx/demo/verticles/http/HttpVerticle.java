package top.yonyong.vertx.demo.verticles.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import top.yonyong.vertx.demo.verticles.eventbus.ServiceVerticle;

public class HttpVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.get("/test").handler(this::testHandler);
    router.get("/eventbus/:msg").handler(this::testEventBus);
    router.route().handler(BodyHandler.create());
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080,this::successHandler);
    System.out.println("start ...");
  }

  private void successHandler(AsyncResult<HttpServer> result) {
    if (result.succeeded()) {
      System.out.println("listen start");
    } else {
      System.out.println(result.cause().toString());
    }
  }

  @Override
  public void stop() throws Exception {
    System.out.println("stop ...");
  }

  private void testHandler(RoutingContext context) {
    System.out.println("request test");
    try {
      Thread.sleep(4000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    context.response().end(new JsonObject().put("status", 200).put("msg", "welcome").toString());
  }

  private void testEventBus(RoutingContext context) {
    EventBus eb = vertx.eventBus();
    String msg = context.pathParam("msg");
    eb.send(ServiceVerticle.class.getName(), msg, reply -> {
      if (reply.succeeded()) {
        Object body = reply.result().body();
        System.out.println("Received reply " + body);
        context.response().end(new JsonObject().put("status", 200).put("msg", body).toString());
      } else {
        System.out.println("No reply");
      }
    });
  }
}
