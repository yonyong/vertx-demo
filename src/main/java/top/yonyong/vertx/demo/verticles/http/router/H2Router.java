package top.yonyong.vertx.demo.verticles.http.router;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class H2Router {

  public static Router createRouter(Vertx vertx){
    Router router = Router.router(vertx);
    router.route("/query/:id").handler(H2Router::handleQuery);
    return router;
  }

  private static void handleQuery(RoutingContext context) {

  }
}

