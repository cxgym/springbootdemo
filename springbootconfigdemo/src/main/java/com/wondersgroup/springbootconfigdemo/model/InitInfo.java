package com.wondersgroup.springbootconfigdemo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "initinfo")         //@Component一定要添加，不然@ConfigurationProperties会报错
@Validated
/*@PropertySource 注解使用yml文件读取属性
* Yaml(yml)最近比较流行的配置文件,相对properties,配置文件结构更清晰简洁.前段时间项目需要引入的配置，于是想用yml文件来增加新的属性配置，新增属性放在application.yml中是没问题的，但是放其他文件中，然后通过@PropertySource 引入时，却出现了问题，所有.yml中的参数配置全部读取无效，properties文件是正常的，后来在stackoverflow上看到@PropertySource中存在factory参数，通过配置factory参数可以达到我们想要的效果。
@PropertySource factory属性的factory默认配置是Class<? extends PropertySourceFactory> factory() default PropertySourceFactory.class;, 我们再看下PropertySourceFactory的源码就可知道了,
* 我们所有做的只需继承DefaultPropertySourceFactory,然后对createPropertySource作下微调,就可以支持yaml了.

 public class MixPropertySourceFactory extends DefaultPropertySourceFactory {

  @Override
  public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
    String sourceName = name != null ? name : resource.getResource().getFilename();
    if (!resource.getResource().exists()) {
      return new PropertiesPropertySource(sourceName, new Properties());
    } else if (sourceName.endsWith(".yml") || sourceName.endsWith(".yaml")) {
      Properties propertiesFromYaml = loadYml(resource);
      return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    } else {
      return super.createPropertySource(name, resource);
    }
  }

  private Properties loadYml(EncodedResource resource) throws IOException {
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(resource.getResource());
    factory.afterPropertiesSet();
    return factory.getObject();
  }
}
* 这里做了个简单的文件后缀判断,如果是以.yml或.yaml结尾,则通过YamlPropertiesFactoryBean加载,其他情况则采用默认方式加载.如果大家需要支持json或xml等其他格式,也可在这里自动加入策略处理.
最后在SpringApplication上加入配置
@PropertySource(value = {
   "a.properties",
   "b.yml"
}, factory = MixPropertySourceFactory.class)
就可以了
* */
//这里有个坑就是默认@PropertySource只支持properties的文件读取，支持yml等其他的读取需要自己重写接口实现，为了避坑我们用properties
//指定映射配置文件的位置,如果注释这段代码，则读取qpplication.yml配置，否则读取initinfo.properties配置
@PropertySource(value = {"classpath:/config/initinfo-${spring.profiles.active:value}.properties"})
public class InitInfo {

    private String appName;
    private Integer appAge;
    private boolean onLine;
    private Date lineDate;
    private Version versionInfo;
    private List<Object> listStar;
    private Map<String,Object> mapMenu;

    //@Email  //该字段开启email校验
    private String email;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getAppAge() {
        return appAge;
    }

    public void setAppAge(Integer appAge) {
        this.appAge = appAge;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

    public Date getLineDate() {
        return lineDate;
    }

    public void setLineDate(Date lineDate) {
        this.lineDate = lineDate;
    }

    public Version getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(Version versionInfo) {
        this.versionInfo = versionInfo;
    }

    public List<Object> getListStar() {
        return listStar;
    }

    public void setListStar(List<Object> listStar) {
        this.listStar = listStar;
    }

    public Map<String, Object> getMapMenu() {
        return mapMenu;
    }

    public void setMapMenu(Map<String, Object> mapMenu) {
        this.mapMenu = mapMenu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "InitInfo{" +
                "appName='" + appName + '\'' +
                ", appAge=" + appAge +
                ", onLine=" + onLine +
                ", lineDate=" + lineDate +
                ", versionInfo=" + versionInfo +
                ", listStar=" + listStar +
                ", mapMenu=" + mapMenu +
                ", email=" + email +
                '}';
    }
}
