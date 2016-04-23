package in.clouthink.daas.audit.sample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "audit示例")
@Controller
@RequestMapping(value = "/sample")
public class SampleController {

	@ApiOperation(value = "测试正常请求")
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello() {
		return "hello world!";
	}

	@ApiOperation(value = "测试异常请求")
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	@ResponseBody
	public String error() {
		throw new RuntimeException("运行时异常");
	}

}
