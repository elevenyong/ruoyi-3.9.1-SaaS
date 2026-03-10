package com.ruoyi.hh;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
//import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
//import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
//import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
//import org.deeplearning4j.nn.conf.layers.DenseLayer;
//import org.deeplearning4j.nn.conf.layers.OutputLayer;
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
//import org.deeplearning4j.nn.weights.WeightInit;
//import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

public class CoalGasPredictionWithWeight {
    public static void main(String[] args) throws IOException, InterruptedException {
//        // 加载数据
//        int numLinesToSkip = 0;
//        char delimiter = ',';
//        RecordReader rr = new CSVRecordReader(numLinesToSkip, delimiter);
//        rr.initialize(new FileSplit(new File("E:\\华鸿慧中\\文档\\预测相关文献\\原始数据.xls")));
//
//        int labelIndex = 5;
//        int numClasses = 2;
//        int batchSize = 16;
//        DataSetIterator iterator = new RecordReaderDataSetIterator(rr, batchSize, labelIndex, numClasses);
//
//        // 划分训练集和测试集
//        DataSet allData = iterator.next();
//        allData.shuffle();
//        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.8);
//        DataSet trainingData = testAndTrain.getTrain();
//        DataSet testData = testAndTrain.getTest();
//
//        // 数据标准化
//        DataNormalization normalizer = new NormalizerStandardize();
//        normalizer.fit(trainingData);
//        normalizer.transform(trainingData);
//        normalizer.transform(testData);
//
//        // 构建神经网络模型
//        int numInputs = 5;
//        int numHiddenNodes = 10;
//        // 定义样本权重
//        double[] classWeights = {1.0, 2.0}; // 非突出样本（0）权重1，突出样本（1）权重2
//        INDArray classWeightsArray = Nd4j.create(classWeights);
//
//        // 构建神经网络模型，使用自定义损失函数
//        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
//                .seed(123)
//                .weightInit(WeightInit.XAVIER)
//                .updater(new Adam())
//                .list()
//                .layer(new DenseLayer.Builder()
//                        .nIn(5) // 输入特征数，根据实际数据调整
//                        .nOut(10) // 隐藏层神经元数
//                        .activation(Activation.RELU)
//                        .build())
//                .layer(new OutputLayer.Builder()
//                        .lossFunction(new WeightedLossFunction(classWeightsArray)) // 应用自定义损失函数
//                        .activation(Activation.SOFTMAX)
//                        .nOut(2) // 二分类，输出2个类别
//                        .build())
//                .build();
//
//        MultiLayerNetwork model = new MultiLayerNetwork(conf);
//        model.init();
//        model.setListeners(new ScoreIterationListener(10));
//
//        // 训练模型
//        int numEpochs = 50;
//        for (int i = 0; i < numEpochs; i++) {
//            model.fit(trainingData);
//        }
//
//        // 评估模型
//        Evaluation eval = new Evaluation(numClasses);
//        INDArray output = model.output(testData.getFeatures());
//        eval.eval(testData.getLabels(), output);
//        System.out.println(eval.stats());
//
//        // 进行预测
//        INDArray prediction = model.output(testData.getFeatures());
//        System.out.println("预测结果: " + prediction);
    }
}