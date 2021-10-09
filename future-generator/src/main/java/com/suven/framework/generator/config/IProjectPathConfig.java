package com.suven.framework.generator.config;

public interface IProjectPathConfig {

    /**
     *  编号1 获取生成RPC项目的代码根目录;
     * @return
     */
    public String getRpcProjectPath() ;

    /**
     * 编号2  获取生成HTTP项目的代码根目录;
     * @return
     */
    public String getHttpProjectPath() ;

    /**
     * 编号3 获取生成API项目的代码根目录;
     * @return
     */

    public String getApiProjectPath() ;


    /**
     *  编号4 获取代码生成模板的根目录;
     * @return
     */
    public String getTemplatesPath() ;

    /**
     * 根据编号获取对应的根目录
     *  编号0 获取代码生成模板的根目录;
     *  编号1 获取生成RPC项目的代码根目录;
     *  编号2  获取生成HTTP项目的代码根目录;
     *  编号3 获取生成API项目的代码根目录;
     *
     * @param projectIndex
     * @return
     */
  default public String getProjectPath(int projectIndex){
        String outFilePath = null;
        switch (projectIndex){
            case 0:
                if(null !=  this.getTemplatesPath()){
                    outFilePath = this.getTemplatesPath();
                }
                break;
            case 1:
                if(null !=  this.getRpcProjectPath()){
                    outFilePath = this.getRpcProjectPath();
                }
                break;
            case 2:
                if(null !=  this.getApiProjectPath()){
                    outFilePath = this.getApiProjectPath();
                }
                break;
            case 3:
                if( null !=  this.getHttpProjectPath()){
                    outFilePath = this.getHttpProjectPath();
                }
                break;

        }
        if(outFilePath == null){
            outFilePath = outFilePath.trim();
        }
        return outFilePath;
    }


}
