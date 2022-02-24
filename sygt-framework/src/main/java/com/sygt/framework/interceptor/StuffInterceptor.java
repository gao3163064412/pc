package com.sygt.framework.interceptor;

import com.sygt.common.annotation.AutoStuff;
import com.sygt.common.enums.AnnoEnum;
import com.sygt.common.enums.FieldEnum;
import com.sygt.common.utils.ReflectUtil;
import com.sygt.framework.web.service.DefaultOperInfoImpl;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Properties;

/**
 * 拦截器实现
 * @date: 2021/05/20 10:51:52
 * @author : zhang'ai'jun
 */
@Intercepts({@Signature(type = Executor.class, method = "update"
        , args = {MappedStatement.class, Object.class})})
public class StuffInterceptor implements Interceptor {

    @Autowired
    private DefaultOperInfoImpl operInfo;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof Executor && invocation.getArgs().length == 2) {

            final Executor executor = (Executor) invocation.getTarget();
            // 获取第一个参数
            final MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
            final Object paramObj = invocation.getArgs()[1];
            if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
                return this.executeInsert(executor, ms, paramObj);
            } else if (ms.getSqlCommandType() == SqlCommandType.UPDATE ) {
                return this.executeUpdate(executor, ms, paramObj);
            }else if (ms.getSqlCommandType() == SqlCommandType.DELETE) {

            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(final Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //
    }

    public  void setOperInfo(DefaultOperInfoImpl operInfo){
        this.operInfo=operInfo;
    }


    /**
     * 新增操作
     *
     * @param executor executor
     * @param ms       ms
     * @param paramObj 参数
     * @return 返回执行结果
     */
    private Object executeInsert(final Executor executor, final MappedStatement ms, final Object paramObj) throws Exception {
        // 获取所有字段
        final Field[] fields = ReflectUtil.getAllFields(paramObj);
        for(final Field field : fields){
            // 设置该字段对象的可访问标志
            field.setAccessible(true);
            // 判断字段是否有我们的AutoStuff注解
            if(field.isAnnotationPresent(AutoStuff.class)){
                // 获取该注解
                AutoStuff autoStuff = field.getAnnotation(AutoStuff.class);
                // 获取字段类型（String.class、Long.class等等）
                Type fieldType = field.getAnnotatedType().getType();
                // 获取注解里写的annoType
                String annoType = autoStuff.annoType().getAnnoType();
                // 先进行字段类型判断，再判断归属，所有信息都是通过operInfo接口获取的
                if(fieldType.equals(FieldEnum.STRING.getFieldType())){
                    if(annoType.equals(AnnoEnum.CREATE_USER.getAnnoType()) ){
                        field.set(paramObj, operInfo.getOperUserString());
                    }else if(annoType.equals(AnnoEnum.CREATE_NAME.getAnnoType()) ){
                        field.set(paramObj, operInfo.getOperUserNameString());
                    }else  if(annoType.equals(AnnoEnum.DEL_FLAG.getAnnoType()) ){
                        field.set(paramObj, operInfo.getDelFlagByInteger().toString());
                    }else if(annoType.equals(AnnoEnum.VERSION.getAnnoType()) ){
                        field.set(paramObj, operInfo.getVersionByInteger().toString());
                    }
                }else if(fieldType.equals(FieldEnum.LONG.getFieldType())){
                    if(annoType.equals(AnnoEnum.CREATE_USER.getAnnoType()) ){
                        field.set(paramObj, operInfo.getOperUserLong());
                    }else if(annoType.equals(AnnoEnum.ID.getAnnoType()) ){
                        field.set(paramObj, operInfo.getId());
                    }else if(annoType.equals(AnnoEnum.DEL_FLAG.getAnnoType()) ){
                        field.set(paramObj, operInfo.getDelFlagByLong());
                    }else if(annoType.equals(AnnoEnum.VERSION.getAnnoType()) ){
                        field.set(paramObj, operInfo.getVersionByLong());
                    }
                }else if(fieldType.equals(FieldEnum.DATE.getFieldType())){
                    field.set(paramObj, operInfo.getDate());
                }else if(fieldType.equals(FieldEnum.TIMESTAMP.getFieldType())){
                    field.set(paramObj, operInfo.getTimestamp());
                }else if(fieldType.equals(FieldEnum.INTEGER.getFieldType())){
                    if(annoType.equals(AnnoEnum.DEL_FLAG.getAnnoType()) ){
                        field.set(paramObj, operInfo.getDelFlagByInteger());
                    }else if(annoType.equals(AnnoEnum.VERSION.getAnnoType()) ){
                        field.set(paramObj, operInfo.getVersionByInteger());
                    }
                }
            }
        }
        return executor.update(ms, paramObj);
    }

    /**
     * 修改操作
     *
     * @param executor executor
     * @param ms       ms
     * @param paramObj 参数
     * @return 返回执行结果
     */
    private Object executeUpdate(final Executor executor, final MappedStatement ms, final Object paramObj) throws Exception {
        final Field[] fields = ReflectUtil.getAllFields(paramObj);
        for(final Field field : fields){
            field.setAccessible(true);
            if(field.isAnnotationPresent(AutoStuff.class)){
                AutoStuff autoStuff = field.getAnnotation(AutoStuff.class);
                Type fieldType = field.getAnnotatedType().getType();
                String annoType = autoStuff.annoType().getAnnoType();

                if(fieldType.equals(FieldEnum.STRING.getFieldType())){
                    if(annoType.equals(AnnoEnum.UPDATE_USER.getAnnoType())){
                        field.set(paramObj, operInfo.getOperUserString());
                    }else if(annoType.equals(AnnoEnum.UPDATE_NAME.getAnnoType()) ){
                        field.set(paramObj, operInfo.getOperUserNameString());
                    }
                }else if(fieldType.equals(FieldEnum.LONG.getFieldType())){
                    if(annoType.equals(AnnoEnum.UPDATE_USER.getAnnoType())){
                        field.set(paramObj, operInfo.getOperUserLong());
                    }
                }else if(fieldType.equals(FieldEnum.DATE.getFieldType()) && annoType.equals(AnnoEnum.UPDATE_TIME.getAnnoType())){
                    field.set(paramObj, operInfo.getDate());
                }else if(fieldType.equals(FieldEnum.TIMESTAMP.getFieldType()) && annoType.equals(AnnoEnum.UPDATE_TIME.getAnnoType())){
                    field.set(paramObj, operInfo.getTimestamp());
                }
            }

        }
        return executor.update(ms, paramObj);
    }

}
