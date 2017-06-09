package it.neef.tub.dima.ba.imwa.impl.calc;

import it.neef.tub.dima.ba.imwa.interfaces.calc.IRelevanceAggregator;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDataType;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.functions.KeySelector;

/**
 * Class for aggregating relevance scores.
 *
 * @see IRelevanceAggregator
 * Created by gehaxelt on 27.10.16.
 */
public class RelevanceAggregator implements IRelevanceAggregator<IDataType, Double, IRelevanceScore<IDataType, Double>> {
    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> add(DataSet<IRelevanceScore<IDataType, Double>> left, DataSet<IRelevanceScore<IDataType, Double>> right) {
        return left.join(right).where(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).equalTo(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).with(new JoinFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> join(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore, IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore2) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() + iDataTypeDoubleIRelevanceScore2.getRelevanceScore()));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> sub(DataSet<IRelevanceScore<IDataType, Double>> left, DataSet<IRelevanceScore<IDataType, Double>> right) {
        return left.join(right).where(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).equalTo(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).with(new JoinFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> join(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore, IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore2) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() - iDataTypeDoubleIRelevanceScore2.getRelevanceScore()));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> mul(DataSet<IRelevanceScore<IDataType, Double>> left, DataSet<IRelevanceScore<IDataType, Double>> right) {
        return left.join(right).where(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).equalTo(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).with(new JoinFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> join(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore, IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore2) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() * iDataTypeDoubleIRelevanceScore2.getRelevanceScore()));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> div(DataSet<IRelevanceScore<IDataType, Double>> left, DataSet<IRelevanceScore<IDataType, Double>> right) {
        return left.join(right).where(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).equalTo(new KeySelector<IRelevanceScore<IDataType, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                return iDataTypeDoubleIRelevanceScore.getIdentifier();
            }
        }).with(new JoinFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> join(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore, IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore2) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() / iDataTypeDoubleIRelevanceScore2.getRelevanceScore()));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> addValue(DataSet<IRelevanceScore<IDataType, Double>> data, final Double value) {
        return data.map(new MapFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> map(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() + value));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> subValue(DataSet<IRelevanceScore<IDataType, Double>> data, final Double value) {
        return data.map(new MapFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> map(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() - value));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> mulValue(DataSet<IRelevanceScore<IDataType, Double>> data, final Double value) {
        return data.map(new MapFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> map(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() * value));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public DataSet<IRelevanceScore<IDataType, Double>> divValue(DataSet<IRelevanceScore<IDataType, Double>> data, final Double value) {
        return data.map(new MapFunction<IRelevanceScore<IDataType, Double>, IRelevanceScore<IDataType, Double>>() {
            @Override
            public IRelevanceScore<IDataType, Double> map(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() / value));
                return iDataTypeDoubleIRelevanceScore;
            }
        });
    }

    @Override
    public Double sum(DataSet<IRelevanceScore<IDataType, Double>> data) {
        try {
            return data.reduce(new ReduceFunction<IRelevanceScore<IDataType, Double>>() {
                @Override
                public IRelevanceScore<IDataType, Double> reduce(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore, IRelevanceScore<IDataType, Double> t1) throws Exception {
                    iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) (iDataTypeDoubleIRelevanceScore.getRelevanceScore() + t1.getRelevanceScore()));
                    return iDataTypeDoubleIRelevanceScore;
                }
            }).collect().get(0).getRelevanceScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Double min(DataSet<IRelevanceScore<IDataType, Double>> data) {
        try {
            return data.reduce(new ReduceFunction<IRelevanceScore<IDataType, Double>>() {
                @Override
                public IRelevanceScore<IDataType, Double> reduce(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore, IRelevanceScore<IDataType, Double> t1) throws Exception {
                    if (iDataTypeDoubleIRelevanceScore.getRelevanceScore() < t1.getRelevanceScore()) {
                        return iDataTypeDoubleIRelevanceScore;
                    } else {
                        iDataTypeDoubleIRelevanceScore.setRelevanceScore((Double) t1.getRelevanceScore());
                        return iDataTypeDoubleIRelevanceScore;
                    }
                }
            }).collect().get(0).getRelevanceScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Double max(DataSet<IRelevanceScore<IDataType, Double>> data) {
        try {
            return data.reduce(new ReduceFunction<IRelevanceScore<IDataType, Double>>() {
                @Override
                public IRelevanceScore<IDataType, Double> reduce(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore, IRelevanceScore<IDataType, Double> t1) throws Exception {
                    if (iDataTypeDoubleIRelevanceScore.getRelevanceScore() > t1.getRelevanceScore()) {
                        return iDataTypeDoubleIRelevanceScore;
                    } else {
                        iDataTypeDoubleIRelevanceScore.setRelevanceScore(t1.getRelevanceScore());
                        return iDataTypeDoubleIRelevanceScore;
                    }
                }
            }).collect().get(0).getRelevanceScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IDataType getByIdentifier(DataSet<IRelevanceScore<IDataType, Double>> data, final long identifier) {
        try {
            return data.filter(new FilterFunction<IRelevanceScore<IDataType, Double>>() {
                @Override
                public boolean filter(IRelevanceScore<IDataType, Double> iDataTypeDoubleIRelevanceScore) throws Exception {
                    return iDataTypeDoubleIRelevanceScore.getIdentifier() == identifier;
                }
            }).collect().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
