package web.Servlet;


public class OrderRoomServlet implements Servlet {
	@Override
	public void service() {
		System.out.println("OrderRoomServlet 订单房间服务被调用了--");
	}
}
