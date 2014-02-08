package org.team537.fms;

public class Stats
{

    long sum;
    long count;
    long min, max;
    double mean, pvar, var, sdev;

public Stats()
{
    clear();
}

public void clear()
{
    count = 0;
    mean = 0.0;
    sum = 0;
    var = 0.0;
    pvar = 0.0;
    sdev = 0.0;
    min = Long.MAX_VALUE;
    max = Long.MIN_VALUE;
}

public void add(long datapt)
{
    double oldmean = mean;

    sum += datapt;
    count++;
    if (datapt >= max)
        max = datapt;

    if (datapt <= min) 
        min = datapt;

    mean += (datapt - oldmean) / count;
    pvar += (datapt - oldmean) * (datapt - mean);

    if (1 < count) {
        var = pvar / (count - 1);
        sdev = Math.sqrt( var );
    }
}

public double Mean()
{
    return mean;
}

public long Count()
{
    return count;
}

public long Sum()
{
    return sum;
}

public long Min()
{
    return min;
}

public long Max()
{
    return max;
}

public double Variance()
{
    return var;
}

public double StdDev()
{
    return sdev;
}
}
