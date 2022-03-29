/**
 *    https://gitee.com/gitsc/pro-cloud/
 *     @Author Aijm 2929793435@qq.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suven.framework.common.api.IBaseApi;

/**
 * @Author Aijm
 * @Description 封装ServiceImpl
 * @Date 2019/10/11
 */
public class MyBatisBaseServiceImpl<M extends BaseMapper<T>, T extends IBaseApi> extends ServiceImpl<M, T>  implements IService<T> {

    @Override
    public boolean save(T entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }
}