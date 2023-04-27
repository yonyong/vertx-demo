package top.yonyong.vertx.demo.verticles.http.router;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.validation.HTTPRequestValidationHandler;
import io.vertx.ext.web.api.validation.ParameterType;
import top.yonyong.vertx.demo.verticles.eventbus.ServiceVerticle;

public class TestRouter {
  private static Vertx vertx;

  public static Router createRouter(Vertx vertx){
    TestRouter.vertx = vertx;
    Router router = Router.router(vertx);
    router.get("/hello").handler(TestRouter::helloHandler);
    HTTPRequestValidationHandler handler = HTTPRequestValidationHandler.create()
      .addPathParam("msg", ParameterType.INT);
    router.get("/eventbus/:msg").handler(handler).handler(TestRouter::eventBusHandler);
    return router;
  }

  private static void helloHandler(RoutingContext context) {
    System.out.println("request test");
    context.response().end(new JsonObject().put("status", 200).put("msg", "welcome").toString());
  }

  private static void eventBusHandler(RoutingContext context) {
    EventBus eb = vertx.eventBus();
    String msg = context.pathParam("msg");
    eb.request(ServiceVerticle.class.getName(), msg, reply -> {
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

