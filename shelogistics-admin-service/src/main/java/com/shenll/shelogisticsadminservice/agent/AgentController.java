package com.shenll.shelogisticsadminservice.agent;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelogistics")
public class AgentController {
    @Autowired
    AgentService agentService;

    @PostMapping("/createAgent")
    public CommonResponse createAgent(@RequestBody AgentDTO agentDTO) {
        return agentService.createAgent(agentDTO);
    }

    @GetMapping("/getAllAgent")
    public CommonResponse getAllAgent() {
        return agentService.findAllAgent();
    }

    @GetMapping("/getAgent/{id}")
    public CommonResponse findAgentById(@PathVariable String id) {
        return agentService.getAgentById(id);
    }

    @PutMapping("/updateAgent/{id}")
    public CommonResponse updateAgent(@RequestBody AgentDTO agentDTO, @PathVariable String id) {
        return agentService.update(agentDTO, id);
    }

    @DeleteMapping("/delete/agent")
    public CommonResponse delete(@RequestParam String id) {
        return agentService.deleteAgent(id);
    }

    @GetMapping("/get/agent/filter")
    public CommonResponse agentFilter(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String code,
                                      @RequestParam(required = false) String city,
                                      @RequestParam(required = false) String phone,
                                      @RequestParam(required = false) Long page,
                                      @RequestParam(required = false) Long size) {
        return agentService.getAgentWithFilters(name, code, city, phone, page, size);
    }

}
