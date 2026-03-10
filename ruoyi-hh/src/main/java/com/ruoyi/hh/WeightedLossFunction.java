package com.ruoyi.hh;

import org.nd4j.common.primitives.Pair;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.activations.IActivation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.lossfunctions.ILossFunction;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;


// 自定义加权损失函数（完整实现 ILossFunction 接口）
class WeightedLossFunction implements ILossFunction {

    private final INDArray classWeights;
    private final ILossFunction baseLossFunction;

    public WeightedLossFunction(INDArray classWeights) {
        this.classWeights = classWeights;
        this.baseLossFunction = LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD.getILossFunction();
    }

    @Override
    public double computeScore(INDArray labels, INDArray preOutput, IActivation activationFn, INDArray mask, boolean average) {
        INDArray weightedLabels = labels.mulColumnVector(classWeights);
        return baseLossFunction.computeScore(weightedLabels, preOutput, activationFn, mask, average);
    }

    @Override
    public INDArray computeScoreArray(INDArray labels, INDArray preOutput, IActivation activationFn, INDArray mask) {
        INDArray weightedLabels = labels.mulColumnVector(classWeights);
        return baseLossFunction.computeScoreArray(weightedLabels, preOutput, activationFn, mask);
    }

    @Override
    public INDArray computeGradient(INDArray labels, INDArray preOutput, IActivation activationFn, INDArray mask) {
        INDArray weightedLabels = labels.mulColumnVector(classWeights);
        return baseLossFunction.computeGradient(weightedLabels, preOutput, activationFn, mask);
    }

    @Override
    public Pair<Double, INDArray> computeGradientAndScore(INDArray labels, INDArray preOutput, IActivation activationFn, INDArray mask, boolean average) {
        return null;
    }

    @Override
    public String name() {
        return "Weighted" + baseLossFunction.name();
    }
}