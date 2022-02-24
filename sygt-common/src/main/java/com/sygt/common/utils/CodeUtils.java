package com.sygt.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CodeUtils {

    /**
     * 生成规则设备编号:设备类型+五位编号（从1开始，不够前补0）
     *
     * @param equipmentNo
     * 				最新设备编号
     * @return
     */
    public static String getNewNo(String equipmentNo,String name){
        String mainTopName = name;
        String newEquipmentNo = mainTopName + "0000001";

        if((equipmentNo != null) && !equipmentNo.isEmpty()){
            int no = Integer.parseInt(equipmentNo.substring(2));
            int newEquipment = ++no;
            newEquipmentNo = String.format(mainTopName + "%07d", newEquipment);
        }

        return newEquipmentNo;
    }

    /**
     * 生成规则设备编号:设备类型+五位编号（从1开始，不够前补0）
     *
     * @param equipmentNo
     * 				最新设备编号
     * @return
     */
    public static String getTestNewNo(String equipmentNo,String name){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date());
        String mainTopName = name;
        String newEquipmentNo = mainTopName + date + "0001";
        if((equipmentNo != null) && !equipmentNo.isEmpty()){
            int no = Integer.parseInt(equipmentNo.substring(10));
            int newEquipment = ++no;
            newEquipmentNo = String.format(mainTopName +date+ "%04d", newEquipment);
        }

        return newEquipmentNo;
    }

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date());
        String mainTopName = "ST";
        StringBuffer equipmentNo = new StringBuffer().append(mainTopName).append(date).append("0001");
//        String equipmentNo = "ST0199";
        String newEquipmentNo = mainTopName + "0001";

        if((equipmentNo != null) && !equipmentNo.toString().isEmpty()){
            int no = Integer.parseInt(equipmentNo.substring(10));
            int newEquipment = ++no;
            System.out.println(newEquipment);
            newEquipmentNo = String.format(mainTopName +date+ "%04d", newEquipment);
        }
        System.out.println(newEquipmentNo);
    }

    /**
     * * 动态生成编号
     *
     * @param code 需要拆分编号的依据 如：hc
     * @param  findNumber：从数据库中查询的最后一条数据
     */
    public static String generateCode(String findNumber,String code){
        String number="";

        if(findNumber!=null){
            String num=findNumber;
            String[] nums= num.split(code);
            Long equNum=Long.valueOf(nums[1])+1;
            //判断是否为本月第一天
            if(isFirstDay()){
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");//可以方便地修改日期格式
                String curr = dateFormat.format( now ).replaceAll("-","");
                if(!findNumber.contains(curr)){
                    code = code + curr;
                    equNum = 1l;
                }
            }
            if(equNum < 10){
                number=code+"00000"+equNum;
            }else if(equNum < 100){
                number=code+"0000"+equNum;
            }else if(equNum < 1000){
                number=code+"000"+equNum;
            }else if(equNum < 10000){
                number=code+"00"+equNum;
            }else if(equNum < 100000){
                number=code+"0"+equNum;
            }else{
                number=code+equNum;
            }
        }else {
            number= generateNum(code);
        }
        return number;
    }

    //返回编号
    public static String generateNum(String code){
        String number = "";
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");//可以方便地修改日期格式
        String curr = dateFormat.format( now ).replaceAll("-","");
        code = code + curr;
        number=code+"000001";
        return number;
    }

    //TODO 判断当前日期是否是本月第一天
    public static boolean isFirstDay(){
        Boolean isTrue = false;
        // TODO Auto-generated method stub
//        System.out.println("当前日期:" + curr);
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        if(date == 1)
            isTrue = true;
        else
            isTrue = false;
        return isTrue;
    }

}
