package com.veryworks.iyeongjun.seoulssul.Domain;

/**
 * Created by iyeongjun on 2017. 8. 24..
 */
public class SeoulData
{
    private SearchConcertDetailService SearchConcertDetailService;

    public SearchConcertDetailService getSearchConcertDetailService ()
    {
        return SearchConcertDetailService;
    }

    public void setSearchConcertDetailService (SearchConcertDetailService SearchConcertDetailService)
    {
        this.SearchConcertDetailService = SearchConcertDetailService;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchConcertDetailService = "+SearchConcertDetailService+"]";
    }
}