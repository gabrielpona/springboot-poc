package dev.vfcardoso.poc.business.repositories.custom;

import dev.vfcardoso.poc.helper.arch.datatables.DataTables;
import dev.vfcardoso.poc.helper.datatables.DtUser;

import java.lang.reflect.InvocationTargetException;

public interface UserRepositoryCustom {
    DataTables<DtUser> listJson(DtUser dtUsuario, Long associacaoId, int start, int length, int orderColumn, String orderDirection) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException;
}
