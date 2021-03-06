package com.magento.idea.magento2plugin.xml.observer.reference.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.magento.idea.magento2plugin.xml.observer.index.EventObserverFileBasedIndex;
import com.magento.idea.magento2plugin.xml.reference.util.ClassesResultsFiller;
import com.magento.idea.magento2plugin.xml.reference.util.ResolveResultsFiller;
import com.magento.idea.magento2plugin.xml.reference.util.VirtualTypesResultsFiller;

import java.util.List;

/**
 * Created by dkvashnin on 11/3/15.
 */
public class ClassResultsFillerWrapper implements ResolveResultsFiller {
    public static final ResolveResultsFiller INSTANCE = new ClassResultsFillerWrapper();

    @Override
    public void fillResults(PsiElement psiElement, List<ResolveResult> results, String typeName) {
        List<String[]> observersTypesList = FileBasedIndex.getInstance().getValues(
            EventObserverFileBasedIndex.NAME,
            typeName,
            GlobalSearchScope.allScope(psiElement.getProject())
        );

        for (String[] observerTypes: observersTypesList) {
            for (String type: observerTypes) {
                ClassesResultsFiller.INSTANCE.fillResults(psiElement, results, type);
                VirtualTypesResultsFiller.INSTANCE.fillResults(psiElement, results, type);
            }
        }
    }
}
