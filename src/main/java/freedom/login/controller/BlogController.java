package freedom.login.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.free.core.utils.UUIDUtils;
import freedom.login.dao.BlogEntityDao;
import freedom.login.entity.BlogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController {


    @Value("${blog-file-path}")
    private String blogFilePath;
    @Resource
    private BlogEntityDao blogEntityDao;


    @GetMapping("/list")
    public List<BlogEntity> getBlogList(){
        List<BlogEntity> result = blogEntityDao.selectList(new LambdaQueryWrapper<BlogEntity>().eq(BlogEntity::getIsDel, 0)
                .orderByDesc(BlogEntity::getCreatedTime));
        return result;
    }

    @GetMapping("/{id}")
    public BlogEntity getById(@PathVariable String id){
        if(StringUtils.isEmpty(id)){
//            Integer.MAX_VALUE;

            BlogEntity blogEntity = new BlogEntity();
            blogEntity.setName("测试数据你都找出来啦");
            return blogEntity;
        }
        return blogEntityDao.selectById(id);
    }

    @PostMapping("/upload")
    public String add(@RequestParam("file") MultipartFile file){
        String srcName = file.getOriginalFilename();
        if(!srcName.contains("-")){
            return "fileName is not good for me!!";
        }
        String srcNameWithoutExt = srcName.substring(0, srcName.indexOf('.'));
        String[] ss = srcNameWithoutExt.split("-");
        String mdFilePath = blogFilePath + File.separator + srcName;
        File dest = new File(mdFilePath);

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setName(ss[0]);
        blogEntity.setMdFilePath(mdFilePath);
        blogEntity.setId(UUIDUtils.build());
        blogEntity.setCreatedBy(ss[1]);
        blogEntity.setUpdatedBy(ss[1]);
        blogEntity.setCreatedTime(new Date());
        blogEntity.setUpdatedTime(new Date());
        blogEntityDao.insert(blogEntity);
        try {
            // 创建临时文件
            File tempFile = File.createTempFile("temp", null);
            // 将 MultipartFile 内容写入临时文件
            file.transferTo(tempFile);

            FileSystemUtils.copyRecursively(tempFile, dest);
        } catch (IOException e) {
            log.error("wirte file error... ", e);
            return FAIL;
        }
        return SUCCESS;
    }

    private final static String SUCCESS = "success";

    private final static String FAIL = "fail";


}
