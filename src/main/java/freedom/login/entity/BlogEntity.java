package freedom.login.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.free.base.entity.BasePo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Data
@NoArgsConstructor
@TableName("blog")
public class BlogEntity extends BasePo {

    private String name;

    private String mdFilePath;
}
