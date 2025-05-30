package org.it611.das.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.domain.*;
import org.it611.das.vo.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public final class Vo2PoUtil {

    public static User userVo2Po(UserVO userVo){

        return new User(IdUtil.getUserId(), userVo.getPhone(), userVo.getEmail(), MD5Util.generate(userVo.getPassword()), userVo.getIdcard(), userVo.getName(),
                userVo.getSex(), userVo.getNation(), userVo.getDate(), userVo.getAddress(), userVo.getFiles(), 0, TimeUtil.getLocalTime());

    }

    public static Company ConpanyVo2Po(CompanyVO companyVO) {
        return new Company(IdUtil.getUserId(), companyVO.getUsername(),MD5Util.generate(companyVO.getPassword()), companyVO.getCompanyName(), companyVO.getCompanyAddress(),
                companyVO.getCompanyEmail(), companyVO.getLandinePhone(), companyVO.getContact(), companyVO.getPhone(), companyVO.getCreditId(),
                companyVO.getCompanyType(), companyVO.getRepresentative(), companyVO.getEstablishmentTime(), companyVO.getStartTime(), companyVO.getEndTime(),
                companyVO.getBusinessScope(), companyVO.getRegistrationAuthority(), companyVO.getRegistrationTime(), companyVO.getFiles(), 0, TimeUtil.getLocalTime());
    }

    public static DegreeCertificate degreeCertificateVo2Po(HttpServletRequest request, DegreeCertificateVO vo) throws IOException {
        Jedis client = RedisUtil.getJedis();

        String path = vo.getFiles();
        String filesHash = client.get(path);

        String token = CookieUtil.getCookie(request,  CookieUtil.COOKIE_TOKEN_KEY);
        String jsonstr = client.get(token);
        HashMap obj=new ObjectMapper().readValue(jsonstr,HashMap.class);
        String userId = obj.get("id").toString();
        client.close();
        return new DegreeCertificate(IdUtil.getDegreeCertId(), userId,vo.getName(), vo.getSex(), vo.getDate(), vo.getDegreeConferringUnit(), vo.getProfessional(),
                vo.getDegreeName(), vo.getCertId(), vo.getTime(), vo.getFiles(), filesHash, "","0", TimeUtil.getLocalTime());

    }

    public static Video videoVo2Po(HttpServletRequest request, VedioVO vo) throws IOException {

        Jedis client = RedisUtil.getJedis();
        String token = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        String jsonStr= client.get(token);
        HashMap obj = new ObjectMapper().readValue(jsonStr, HashMap.class);
        String userId = obj.get("id").toString();

        String filesHash = "";//方便进行拼接
        String[] filesArr = vo.getFiles().split(",");
        for(int i=0; i<filesArr.length; i++){
            String temp = client.get(filesArr[i]);
            filesHash = filesHash + temp+ ",";
        }
        //去掉,
        filesHash.substring(0, filesHash.length()-1);
        client.close();
        return new Video(IdUtil.getVideoId(), vo.getTitle(), vo.getDes(), userId, vo.getAuthor(), vo.getFiles(), filesHash, "", TimeUtil.getLocalTime(), "0");
    }

    public static Music musicVo2Po(HttpServletRequest request, MusicVO vo) throws IOException {

        Jedis client = RedisUtil.getJedis();
        String token = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        String jsonStr= client.get(token);
        HashMap obj = new ObjectMapper().readValue(jsonStr, HashMap.class);
        String userId = obj.get("id").toString();

        String filesHash = "";//方便进行拼接
        String[] filesArr = vo.getFiles().split(",");
        for(int i=0; i<filesArr.length; i++){
            String temp = client.get(filesArr[i]);
            filesHash = filesHash + temp+ ",";
        }
        //去掉,
        filesHash.substring(0, filesHash.length()-1);
        client.close();
        return new Music(IdUtil.getAudioId(), vo.getTitle(), vo.getDes(), userId, vo.getAuthor(), vo.getFiles(), filesHash, "", TimeUtil.getLocalTime(), "0");
    }


    public static  Photo  photoVo2Po(HttpServletRequest request,PhotoVO vo) throws  Exception {
        Jedis client = RedisUtil.getJedis();
        String token = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        String jsonStr= client.get(token);
        HashMap obj = new ObjectMapper().readValue(jsonStr, HashMap.class);
        String userId = obj.get("id").toString();

        String filesHash = "";//方便进行拼接
        String[] filesArr = vo.getFiles().split(",");
        for(int i=0; i<filesArr.length; i++){
            String temp = client.get(filesArr[i]);
            filesHash = filesHash + temp+ ",";
        }
        //去掉,
        filesHash.substring(0, filesHash.length()-1);
        client.close();
        return new Photo(IdUtil.getImageId(), vo.getTitle(), vo.getDes(), userId, vo.getAuthor(), vo.getFiles(), filesHash, "", TimeUtil.getLocalTime(), "0");
    }

    public static Syxxzl syxxzlVo2Po(HttpServletRequest request, SyxxzlVO vo) throws IOException {
        Jedis client = RedisUtil.getJedis();

        String path = vo.getFiles();
        String filesHash = client.get(path);

        String token = CookieUtil.getCookie(request,  CookieUtil.COOKIE_TOKEN_KEY);
        String jsonstr = client.get(token);
        HashMap obj=new ObjectMapper().readValue(jsonstr,HashMap.class);
        String userId = obj.get("id").toString();
        client.close();
        return new Syxxzl(IdUtil.getSyxxzlId(), userId, vo.getZsh(), vo.getSyxxmc(), vo.getFmr(), vo.getZlh(), vo.getZlsqr(),vo.getZlqr(), vo.getSqggr(), vo.getFzsj() , vo.getFiles(), filesHash, "","0", TimeUtil.getLocalTime());

    }


    public static DrivingLicence drivingLicenceVo2Po(HttpServletRequest request, DrivingLicenceVO vo) throws IOException {
        Jedis client = RedisUtil.getJedis();

        String path = vo.getFiles();
        String filesHash = client.get(path);

        String token = CookieUtil.getCookie(request,  CookieUtil.COOKIE_TOKEN_KEY);
        String jsonstr = client.get(token);
        HashMap obj=new ObjectMapper().readValue(jsonstr,HashMap.class);
        String userId = obj.get("id").toString();
        client.close();
        return new DrivingLicence(IdUtil.getDrivingLicenceId(), userId, vo.getDrivingLicenceId(), vo.getName(), vo.getSex(), vo.getNation(), vo.getAddress(), vo.getDate(),vo.getLzDate(), vo.getZjcx(), vo.getStartDate(), vo.getValidTime(), vo.getHz() , vo.getFiles(), filesHash, "","0", TimeUtil.getLocalTime());

    }





}
