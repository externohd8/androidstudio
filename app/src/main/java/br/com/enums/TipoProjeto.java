package br.com.enums;

import android.content.Context;

import br.com.dao.ProjetoAmostrasDAO;
import br.com.dao.ProjetoCensoDAO;
import br.com.trees.TodosProjetosAmostra;
import br.com.trees.TodosProjetosCenso;

/**
 * Created by Fernando on 22/10/2016.
 */
public enum TipoProjeto {
    //DETERMINA QUAL O TIPO DE PROJETO QUE SERA ENVIANDO E SUAS AÇÕES SOBRE ELE
    //PADRONIZANDO DE ACORDO COM O PROJETO QUE SERÁ ENVIADO

    PROJETO_CENSO(0, TodosProjetosCenso.class) {
        @Override
        public void alterarStatus(Context context, Long idProjeto, Status novoStatus) {
            new ProjetoCensoDAO(context).alterarStatus(idProjeto, novoStatus);
        }
    },
    PROJETO_AMOSTRAS(1, TodosProjetosAmostra.class) {
        @Override
        public void alterarStatus(Context context, Long idProjeto, Status novoStatus) {
            new ProjetoAmostrasDAO(context).alterarStatus(idProjeto, novoStatus);
        }
    };

    private int codigo;

    private Class<?> classeRetorno;

    TipoProjeto (int codigo, Class<?> classeRetorno) {
        this.codigo = codigo;
        this.classeRetorno = classeRetorno;
    }

    public Class<?> getClasseRetorno() {
        return classeRetorno;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoProjeto from(int codigo) {
        for (TipoProjeto tp : TipoProjeto.values()) {
            if (tp.getCodigo() == codigo) {
                return tp;
            }
        }
        return null;
    }

    public abstract void alterarStatus(Context context, Long idProjeto, Status novoStatus);

}
