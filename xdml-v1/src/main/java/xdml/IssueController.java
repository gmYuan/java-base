package xdml;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("repos")
public class IssueController {
    @DeleteMapping("/{owner}/{repo}/issues/{issueNumber}/lock")
    public String unlock(
        @PathVariable("owner") String owner,
        @PathVariable("repo") String repo,
        @PathVariable("issueNumber") String issueNumber
    ) {
        return ("unlocking " + owner + "/" + repo + "#" + issueNumber);
    }

    @PostMapping("/{owner}/{repo}/issues")
    public String create(
        @PathVariable("owner") String owner,
        @PathVariable("repo") String repo,
        @RequestBody MyParam object
    ) {
        System.out.println(object);
        return object.getTitle() + object.getLabels();
    }

    @PostMapping("/login")
    public String login(
        @RequestParam("username") String username,
        @RequestParam("password") Integer password
    ) {
        System.out.println(username + password);
        return username + password;
    }


}
