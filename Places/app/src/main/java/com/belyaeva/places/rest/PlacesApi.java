package com.belyaeva.places.rest;

import com.belyaeva.places.domain.Publication;

public interface PlacesApi {

    void fillPublications();

    void createNewPublication(Publication publication);

}
