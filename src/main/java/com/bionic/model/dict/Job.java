package com.bionic.model.dict;

import com.fasterxml.jackson.annotation.JsonValue;
import javax.persistence.Id;

public enum Job  {
	DRIVER(0), OPERATOR(1);

    @Id
    private Integer jobId;

    Job(final Integer jobId) {
        this.jobId = jobId;
    }

    @JsonValue
    public Integer getJobId() {
        return jobId;
    }

}
