package com.example.budgetbuddytravel.utils;

import com.example.budgetbuddytravel.model.CategorieDepense;
import com.example.budgetbuddytravel.model.Depense;
import com.example.budgetbuddytravel.model.Voyage;

public class Statistiques {

    public static float calculerTotalDepenses(Voyage voyage) {
        float total = 0;
        for (CategorieDepense cat : voyage.getCategories()) {
            for (Depense d : cat.getDepenses()) {
                total += d.getMontant();
            }
        }
        return total;
    }

    public static float calculerBudgetPrevuTotal(Voyage voyage) {
        float total = 0;
        for (CategorieDepense cat : voyage.getCategories()) {
            total += cat.getBudgetPrevu();
        }
        return total;
    }

    public static float calculerMoyenneDepenseParCategorie(Voyage voyage) {
        int nbCat = voyage.getCategories().size();
        if (nbCat == 0) return 0;
        return calculerTotalDepenses(voyage) / nbCat;
    }

    public static float ecartBudgetGlobal(Voyage voyage) {
        return voyage.getBudgetGlobal() - calculerTotalDepenses(voyage);
    }

    public static int nombreDeCategories(Voyage voyage) {
        return voyage.getCategories().size();
    }
}
