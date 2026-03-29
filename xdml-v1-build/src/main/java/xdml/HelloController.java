package xdml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import xdml.entity.RankItem;
import xdml.service.RankService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 返回 HTML 页面用 {@link Controller}；
 * 若用 {@code @RestController}，所有方法都会带 {@code @ResponseBody}，
 * {@link ModelAndView} 会被当成 JSON 写回响应体，不会走 FreeMarker 渲染。
 */
@Controller
public class HelloController {

    @Autowired
    private RankService rankService;

    @RequestMapping("/")
    public ModelAndView index() {
        System.out.println("-----index home get request-----");
        List<RankItem> items = rankService.getRank();
        HashMap<String, Object> model = new HashMap<>();
        model.put("items", items);
        return new ModelAndView("index", model);
    }


    @RequestMapping("/search")
    @ResponseBody
    public Object search( HttpServletRequest request, HttpServletResponse response) {
        return rankService.getRank();
    }

    /** 供 static/index.html 中 XHR 拉取排行榜 JSON */
    @RequestMapping("/rankData")
    @ResponseBody
    public List<RankItem> rankData() {
        return rankService.getRank();
    }






    /** Spring IOC的 基础使用

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/search")
    public String search(
        @RequestParam("q") String searchKey,
        @RequestParam(value="charset", required=false) String charset
    ) {
        return "search: " + searchKey + " charset: " + charset;
    }

    @RequestMapping("/search2")
    public void search2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("search2 is forbidden");
    }

    @RequestMapping("/search3")
    @ResponseBody
    public Object search3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("result", Arrays.asList("result1", "result2", "result3"));
        return map;
    }
    **/
}
