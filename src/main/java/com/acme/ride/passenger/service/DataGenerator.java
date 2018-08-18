package com.acme.ride.passenger.service;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DataGenerator {

    static String[] loc = {"Raleigh Convention Center, Raleigh, NC 27601",
                                "North Carolina State University Campus, Raleigh, NC 27695",
                                "St Augustine University, Raleigh, NC 27610",
                                "Raleigh-Durham International Airport, Morrisville, NC 27560",
                                "North Carolina Biotechnology Center, Durham, NC 27703",
                                "Wake Forest Historical Museum, Wake Forest, NC 27587",
                                "Durham Technical Community College, Durham, NC 27703",
                                "Raleigh GreyHound Bus Station, Raleigh, NC 27604",
                                "Raleigh Executive JetPort, Sanford, NC 27330",
                                "Pfizer Pharmaceutical Manufacturing Plant, Sanford, NC 27330",
                                "North Carolina Museum Of Art, Raleigh, NC 27607",
                                "Research Triangle Park Headquarters, Research Triangle Park, NC 27709",
                                "North Carolina State Capitol, Raleigh, NC 27601",
                                "NC State University Centennial Campus, Raleigh, NC 27606",
                                "Crabtree Valley Mall, Raleigh, NC 27612",
                                "Park West Village, MorrisVille, NC 27560",
                                "Durham Bulls Athletic Park, Durham, NC 27701",
                                "Centennial Authority, Raleigh, NC 27607",
                                "Burlington Athletic Stadium, Burlington, NC 27217",
                                "Red Hat Tower, Raleigh, NC 27601 "};

    public String location() {
        int num = (int)(Math.random() * 20);
        return loc[num];
    }

    public BigDecimal decimal(long start, long end, int scale) {
        BigDecimal s = new BigDecimal(start);
        BigDecimal e = new BigDecimal(end);
        
        BigDecimal r = s.add(new BigDecimal(Math.random()).multiply(e.subtract(s)));
        return r.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    public BigInteger numeric(long min, long max) {
        BigInteger s = BigInteger.valueOf(min);
        BigInteger e = BigInteger.valueOf(max);
        
        BigInteger r = s.add(new BigDecimal(Math.random()).multiply(new BigDecimal(e.subtract(s))).toBigInteger());
        return r;
    }
 
}
