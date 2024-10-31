package com.od.library_management_system.repository;

import com.od.library_management_system.entity.Member;
import com.od.library_management_system.utils.ResponseUtility;
import com.od.library_management_system.utils.TranSqlServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

import static com.od.library_management_system.enums.ResponseCode.ERR_LIB_0002;
import static com.od.library_management_system.utils.Constants.*;

@Repository
public class MemberRepoImpl implements MemberRepo{

    @Autowired
    TranSqlServices tranSqlServices;


    @Override
    public Integer createMember(Member member) {
        Map<String, Object> param = new HashMap<>();
        param.put(NAME, member.getName());
        param.put(PHONE, member.getPhone());
        param.put(REG_DATE, LocalDate.now());

        String sql = "INSERT INTO member (name, phone, registered_date) VALUES (:name, :phone, :registered_date)";
        return tranSqlServices.persist(sql, new MapSqlParameterSource(param));
    }

    @Override
    public boolean isMemberExist(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);
        String sQuery = "select count(id) from member where id=:id";

        return tranSqlServices.getInteger(sQuery, new MapSqlParameterSource(param)) > 0;
    }

    @Override
    public Map<String, Object> getMemberById(Long id) {
        if(!isMemberExist(id)) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(MEMBER, id)
                    ));
        }

        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);
        String sql = "select id, name, phone, registered_date from member where id = :id";
        return tranSqlServices.getMap(sql,  new MapSqlParameterSource(param));
    }


    @Override
    public Integer deleteMemberById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, id);

        if(!isMemberExist(id)) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(MEMBER, id)
            ));
        }

        String sql = "delete from member where id = :id";
        return tranSqlServices.updateDelete(sql, param);
    }

    @Override
    public Integer updateMember(Member member) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, member.getId());

        if(!isMemberExist(member.getId())) {
            throw new NoSuchElementException(
                    ERR_LIB_0002.getMessage(ResponseUtility.getParamMapWithTypeAndId(MEMBER, member.getId())
                    ));
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE member SET ");

        boolean isFirstField = true;

        if (Objects.nonNull(member.getName())) {
            param.put(NAME, member.getName());
            sql.append(isFirstField ? "" : ", ").append("name = :name");
            isFirstField = false;
        }

        if (Objects.nonNull(member.getPhone())) {
            param.put(PHONE, member.getPhone());
            sql.append(isFirstField ? "" : ", ").append("phone = :phone");
            isFirstField = false;
        }

        if (Objects.nonNull(member.getRegisteredDate())) {
            param.put(REG_DATE, member.getRegisteredDate());
            sql.append(isFirstField ? "" : ", ").append("registered_date = :registered_date");
            isFirstField = false;
        }

        sql.append(" WHERE id = :id");

        return tranSqlServices.persist(sql.toString(), new MapSqlParameterSource(param));
    }

    @Override
    public List<Map<String, Object>> getAllMembers() {
        String sql = "select id, name, phone, registered_date from member";
        return tranSqlServices.getList(sql);
    }
}
