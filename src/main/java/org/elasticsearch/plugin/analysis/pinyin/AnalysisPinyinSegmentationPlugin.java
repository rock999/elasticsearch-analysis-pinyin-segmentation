package org.elasticsearch.plugin.analysis.pinyin;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.PinyinAnalysisBinderProcessor;
import org.elasticsearch.plugins.AbstractPlugin;

/**
 * The Pinyin Analysis plugin integrates Pinyin4j(http://pinyin4j.sourceforge.net/) module into elasticsearch.
 */
public class AnalysisPinyinSegmentationPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "analysis-pinyin-segmentation";
    }

    @Override
    public String description() {
        return "Chinese to Pinyin convert support";
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new PinyinAnalysisBinderProcessor());
    }
}
