package ma.enset.metier;

import ma.enset.dao.IDao;
import ma.enset.framework.annotations.Component;

@Component
public class MetierImpl implements IMetier {

    private IDao dao;

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public double calcul() {
        return dao.getData() * 2;
    }
}