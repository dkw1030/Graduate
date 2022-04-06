package com.example.demo.utils;


import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.PageDTO;

import java.util.Arrays;
import java.util.List;

public class PageControlUtil {

    public static PageDTO generatePageDTO(List allList, int curPage){
        PageDTO pageDTO = new PageDTO();
        pageDTO.setCur(curPage);
        pageDTO.setTot(totPage(allList.size()));
        pageDTO.setStyle(setStyle(pageDTO));
        return pageDTO;
    }

    public static int fromIndex(int page){
        int num = Switcher.PageSwitcher.NUM;
        return (page-1) * num;
    }

    public static int toIndex(int page, int size){
        int num = Switcher.PageSwitcher.NUM;
        return Math.min(size, page * num);
    }

    public static int totPage(int size){
        int num = Switcher.PageSwitcher.NUM;
        if(size % num == 0){
            return size/num;
        }else{
            return size/num + 1;
        }
    }

    public static int setStyle(PageDTO pageDTO){
        if(pageDTO.getTot()<=1){
            return 0;
        }
        if(pageDTO.getTot()<=7){
            return 1;
        }else{
            if(pageDTO.getCur()<5){
                return 2;
            }else if(pageDTO.getCur()>= pageDTO.getTot()-4){
                return 4;
            }else{
                return 3;
            }
        }
    }

//    public static void main(String[] args) {
//        List<Integer> l = Arrays.asList(1,2,3,4,5,6,7,8,9);
//
//        int page = 3;
//        int num = 3;
//
//        List<Integer> sub = l.subList(fromIndex(page, num),toIndex(page, num, l.size()));
//        System.out.println(totPage(l.size(), num));
//        for (Integer i:
//             sub) {
//            System.out.println(i);
//        }
//    }
}
