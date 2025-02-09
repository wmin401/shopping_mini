package com.study.reply;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.reply.ReplyDTO;
import com.study.reply.ReplyMapper;
import com.study.utility.Utility;
 
@RestController
public class ReplyController {
  private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
 
  @Autowired
  private ReplyMapper mapper;
  
  @PutMapping("/reviews/reply/{rnum}")
  public ResponseEntity<String> modify(@RequestBody ReplyDTO vo, 
      @PathVariable("rnum") int rnum) {
 
    log.info("rnum: " + rnum);
    log.info("modify: " + vo);
 
    return mapper.update(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
 
  }
 
  @DeleteMapping("/reviews/reply/{rnum}")
  public ResponseEntity<String> remove(@PathVariable("rnum") int rnum) {
 
    log.info("remove: " + rnum);
 
    return mapper.delete(rnum) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
 
  }
  
  @PostMapping("/reviews/reply/create")
  public ResponseEntity<String> create(@RequestBody ReplyDTO vo) {
 
    log.info("ReplyDTO1: " + vo.getContent());
    log.info("ReplyDTO1: " + vo.getId());
    log.info("ReplyDTO1: " + vo.getReviewno());
 
    vo.setContent(vo.getContent().replaceAll("/n/r", "<br>"));
 
    int flag = mapper.create(vo);
 
    log.info("Reply INSERT flag: " + flag);
 
    return flag == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
 
  @GetMapping("/reviews/reply/{rnum}")
  public ResponseEntity<ReplyDTO> get(@PathVariable("rnum") int rnum) {
 
    log.info("get: " + rnum);
 
    return new ResponseEntity<>(mapper.read(rnum), HttpStatus.OK);
  }
 
  @GetMapping("/reviews/reply/list/{reviewno}/{sno}/{eno}")
  public ResponseEntity<List<ReplyDTO>> getList(@PathVariable("reviewno") int reviewno, 
      @PathVariable("sno") int sno,
      @PathVariable("eno") int eno) {
 
    Map map = new HashMap();
    map.put("sno", sno);
    map.put("eno", eno);
    map.put("reviewno", reviewno);
 
    return new ResponseEntity<List<ReplyDTO>>(mapper.list(map), HttpStatus.OK);
  }
 
  @GetMapping("/reviews/reply/page")
  public ResponseEntity<String> getPage(@RequestParam("nPage") int nPage, 
      @RequestParam("nowPage") int nowPage,
      @RequestParam("reviewno") int reviewno, 
      @RequestParam("col") String col, 
      @RequestParam("word") String word) {
 
    int total = mapper.total(reviewno);
    String url = "read";
 
    int recordPerPage = 3; // 한페이지당 출력할 레코드 갯수
 
    String paging = Utility.rpaging(total, nowPage, recordPerPage, col, word, url, nPage, reviewno);
 
    return new ResponseEntity<>(paging, HttpStatus.OK);
 
  }
}