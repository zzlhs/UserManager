package freedom.login.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.free.core.utils.UUIDUtils;
import freedom.login.dao.BlogEntityDao;
import freedom.login.entity.BlogEntity;
import freedom.login.entity.BlogRouterEntity;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    /**
     *
     * {
     *     "subPath": "/",
     *     "title": "markdown-website",
     *     "topicWidth": 250,
     *     "openLevel": 1,
     *     "topics": [
     *     {
     *       "id": "01",
     *       "name": "markdown-website是什么？",
     *       "src": "static/what.md"
     *     },
     *     {
     *         "id": "02",
     *         "name": "开始使用",
     *         "submenus": [
     *         {
     *             "id": "0201",
     *             "name": "搭建markdown网站",
     *             "src": "static/how.md"
     *         },
     *         {
     *             "id": "0102",
     *             "name": "更多网站属性配置",
     *             "src": "static/edit.md"
     *         },
     *         {
     *             "id": "0203",
     *             "name": "如何部署发布",
     *             "src": "static/deploy.md"
     *         },
     *                {
     * 			"id": "0204",
     * 			"name": "链接跳转指定文档",
     * 			"src": "static/link.md"
     *        }
     *       ]
     *     }
     *   ]
     * }
     */
    @GetMapping("/mdRoute")
    public List<BlogRouterEntity> mdRoute(){
        List<BlogEntity> result = blogEntityDao.selectList(new LambdaQueryWrapper<BlogEntity>().eq(BlogEntity::getIsDel, 0)
                .orderByDesc(BlogEntity::getCreatedTime));

        List<BlogRouterEntity> resList = new ArrayList<>();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        result.stream().forEach(item -> {
            BlogRouterEntity blogRouterEntity = new BlogRouterEntity();
            blogRouterEntity.setId(item.getId());
            blogRouterEntity.setName(item.getName()+"_" + ft.format(item.getCreatedTime()));
            blogRouterEntity.setSrc(item.getMdFilePath());
            resList.add(blogRouterEntity);
        });
        return resList;
    }

    public static void main(String[] args) {
    }


    @GetMapping("/list")
    public List<BlogEntity> getBlogList(){
        List<BlogEntity> result = blogEntityDao.selectList(new LambdaQueryWrapper<BlogEntity>().eq(BlogEntity::getIsDel, 0)
                .orderByDesc(BlogEntity::getCreatedTime));
        return result;
    }

    @GetMapping("/{id}")
    public BlogEntity getById(@PathVariable String id){
        if(StringUtils.isEmpty(id)){
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
