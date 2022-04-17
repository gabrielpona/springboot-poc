package dev.vfcardoso.poc.business.dao;

import dev.vfcardoso.poc.helper.arch.datatables.DataTables;
import dev.vfcardoso.poc.helper.arch.datatables.DataTablesHelper;
import dev.vfcardoso.poc.helper.arch.utility.ConvertUtils;
import dev.vfcardoso.poc.helper.datatables.DtUser;
import org.hibernate.CacheMode;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Component
public class UserDtDao extends AbstractDao{



    public UserDtDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public DataTables<DtUser> listJson(DtUser dtUsuario, Long associacaoId, int start, int length, int orderColumn, String orderDirection)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


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

        Query q = this.getSessionFactory().openSession().createSQLQuery(consulta.toString());

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

        q.setCacheMode(CacheMode.REFRESH);

        return new DataTablesHelper<>(DtUser.class).getDatatablesFromRawObjectArray(q.list(), dtUsuario.isEmpty(),
                start, length, this.countAll());
    }

    private int countAll() {
        return ConvertUtils.convertTo(this.getSessionFactory().openSession().createQuery("select count(*) from User ").uniqueResult(), Integer.class);
    }
}
