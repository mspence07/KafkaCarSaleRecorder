package com.carsales.recorder.model;

/**
 * Record for use with handling the body that is passed in as part of the generation REST API
 * endpoint.
 *
 * @param schemaVersion - Version of the schema to use.
 * @param messageCount - The number of messages to be generated.
 */
public record SaleRequest(String schemaVersion, int messageCount) {}
