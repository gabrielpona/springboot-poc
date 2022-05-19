package dev.vfcardoso.poc.business.repositories.impl;

import dev.vfcardoso.poc.business.repositories.custom.UserRepositoryCustom;
import dev.vfcardoso.poc.helper.arch.datatables.DataTables;
import dev.vfcardoso.poc.helper.arch.datatables.DataTablesHelper;
import dev.vfcardoso.poc.helper.arch.utility.ConvertUtils;
import dev.vfcardoso.poc.helper.datatables.DtUser;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public DataTables<DtUser> listJson(DtUser dtUsuario, Long associacaoId, int start, int length, int orderColumn, String orderDirection) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {

        StringBuilder consulta = new StringBuilder();

        consulta.append("select  ")
                .append("   u.id as ROWID, ")
                .append("   case when u.enabled = 1 then 'ATIVO' else 'DESATIVADO' end as STATUS, ")
                .append("   concat(u.firstname,' ',u.lastname)  as NOME, ")
                .append("   u.username as LOGIN, ")
                .append("   u.email as EMAIL ");
        /*        .append("   case ");

        for(Role p : Role.values()) {
            consulta.append("   when u.ROLE = ").append(p.getId()).append(" then '").append(p.toString()).append("' ");
        }
        consulta.append("   end as PAPEL ")
                .append("from ")
                .append("	USUARIO u ");
         */
        consulta.append(" from [dbo].[user] u ");
        consulta.append(" where 1=1 ");

        if (dtUsuario.getDT_RowId() != null && dtUsuario.getDT_RowId() != 0)
            consulta.append("   and u.id = :rowid ");
        if (dtUsuario.getStatus() != null && !Objects.equals(dtUsuario.getStatus(), ""))
            consulta.append("   and (select case when u1.ativo = 1 then 'ATIVO' else 'DESATIVADO' end from USUARIO u1 where u1.ID = u.ID) like :status ");
        if (dtUsuario.getNome() != null && !Objects.equals(dtUsuario.getNome(), ""))
            consulta.append("   and u.firstname like :nome ");
        if (dtUsuario.getLogin() != null && !Objects.equals(dtUsuario.getLogin(), ""))
            consulta.append("   and u.username like :login ");
        if (dtUsuario.getEmail() != null && !Objects.equals(dtUsuario.getEmail(), ""))
            consulta.append("   and u.email like :email ");
        /*
        if (dtUsuario.getPapel() != null && !Objects.equals(dtUsuario.getPapel(), "")) {
            consulta.append("   and (select case ");
            for(Role p : Role.values()) {
                consulta.append("   when u1.ROLE = ").append(p.getId()).append(" then '").append(p.toString()).append("' ");
            }
            consulta.append("   end from USUARIO u1 where u1.ID = u.ID) like :papel ");
        }
         */

        /*
        if(associacaoId>0){
            consulta.append("   and u.ASSOCIACAO_ID = :associacaoId ");
        }
         */

        consulta.append("order by ").append(orderColumn + 2).append(" ").append(orderDirection);

        Query q = this.em.createNativeQuery(consulta.toString());

        if (dtUsuario.getDT_RowId() != null && dtUsuario.getDT_RowId() != 0)
            q.setParameter("rowid", dtUsuario.getDT_RowId());
        if (dtUsuario.getStatus() != null && !Objects.equals(dtUsuario.getStatus(), ""))
            q.setParameter("status", '%' + dtUsuario.getStatus() + '%');
        if (dtUsuario.getNome() != null && !Objects.equals(dtUsuario.getNome(), ""))
            q.setParameter("nome", '%' + dtUsuario.getNome() + '%');
        if (dtUsuario.getLogin() != null && !Objects.equals(dtUsuario.getLogin(), ""))
            q.setParameter("login", '%' + dtUsuario.getLogin() + '%');
        if (dtUsuario.getEmail() != null && !Objects.equals(dtUsuario.getEmail(), ""))
            q.setParameter("email", '%' + dtUsuario.getEmail() + '%');
        //if (dtUsuario.getPapel() != null && !Objects.equals(dtUsuario.getPapel(), ""))
        //    q.setParameter("papel", '%' + dtUsuario.getPapel() + '%');
        /*
        if(associacaoId>0){
            q.setParameter("associacaoId",associacaoId);
        }
         */

        return new DataTablesHelper<>(DtUser.class).getDatatablesFromRawObjectArray(q.getResultList(), dtUsuario.isEmpty(),
                start, length, this.countAll());

    }

    private int countAll() {
        return ConvertUtils.convertTo(this.em.createNativeQuery("select count(*) from User ").getFirstResult(), Integer.class);
    }

}
