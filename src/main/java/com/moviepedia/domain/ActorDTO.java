package com.moviepedia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActorDTO {
	private String actorcode;
    private String moviecode;
    private String actorKname;
    private String actorEname;
    private String actorpart;
    private String actorrole;
    private String actormovie;
    private String actorimg;
}
