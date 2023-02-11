package com.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林于哲
 * @since 2023-02-12
 */
@Getter
@Setter
  @TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("使用者名稱")
      private String username;

      @ApiModelProperty("使用者密碼")
      private String password;

      @ApiModelProperty("使用者暱稱")
      private String nickname;

      @ApiModelProperty("使用者信箱")
      private String email;

      @ApiModelProperty("使用者電話")
      private String phone;

      @ApiModelProperty("使用者地址")
      private String address;

      @ApiModelProperty("建立時間")
      private LocalDateTime createTime;

      @ApiModelProperty("頭像")
      private String avatarUrl;


}
