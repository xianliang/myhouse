package com.example.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author liangxianliang
 * @create 2019-12-03 16:03
 */
@Data
public class House {

    private Long id;
    private Integer type;
    private Integer price;
    private String name;
    private String images;
    private Integer area;
    private Integer beds;
    private Integer baths;
    private Double rating;

    @TableField(exist = false)
    private  Integer roundRating = 0;
    private String remarks;
    private String properties;
    private String floorPlan;
    private String tags;
    private Date createTime;
    private Integer cityId;
    private Integer communityId;
    private String address;

    @TableField(exist = false)
    private String firstImg;

    @TableField(exist = false)
    private List<String> imageList = Lists.newArrayList();

    public void setImages(String images) {
        this.images = images;
        if(!Strings.isNullOrEmpty(images)){
            List<String> list = Splitter.on(",").splitToList(images);
            if(list.size() > 0 ){
                this.firstImg = list.get(0);
                this.imageList = list;
            }
        }
    }

    @TableField(exist = false)
    private List<String> floorPlanList = Lists.newArrayList();
    @TableField(exist = false)
    private List<String> featureList = Lists.newArrayList();

    @TableField(exist = false)
    private List<MultipartFile> houseFiles;
    @TableField(exist = false)
    private List<MultipartFile> floorPlanFiles;

    @TableField(exist = false)
    private String priceStr;

    public void setType(Integer type) {
        this.type = type;
        if(type.equals(1)){
            this.typeStr="For Sale";
        }else{
            this.typeStr="For Rent";
        }
    }

    @TableField(exist = false)
    private String typeStr;
    @TableField(exist = false)
    private Long userId;
    @TableField(exist = false)
    private boolean bookmarked;
    private Integer state;
    @TableField(exist = false)
    private List<Long> ids;
    @TableField(exist = false)
    private String sort = "create_time";
}
