package za.co.carhire.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.reservation.Location;

import java.io.IOException;

@Component
public class LocationDeserializer extends JsonDeserializer<Location> {

  @Override
  public Location deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonNode node = p.getCodec().readTree(p);

    // If it's a string (location name or ID), create a minimal Location
    if (node.isTextual()) {
      String value = node.asText();
      // Try to parse as integer (location ID)
      try {
        int locationId = Integer.parseInt(value);
        return new Location.Builder()
            .setLocationID(locationId)
            .build();
      } catch (NumberFormatException e) {
        // If not a number, it's a location name - store it in locationName
        // The service layer will look it up later
        return new Location.Builder()
            .setLocationName(value)
            .build();
      }
    }

    // If it's a number, use it as location ID
    if (node.isNumber()) {
      int locationId = node.asInt();
      return new Location.Builder()
          .setLocationID(locationId)
          .build();
    }

    // If it's an object, deserialize normally
    if (node.isObject()) {
      Location.Builder builder = new Location.Builder();

      if (node.has("locationID")) {
        builder.setLocationID(node.get("locationID").asInt());
      }
      if (node.has("locationName")) {
        builder.setLocationName(node.get("locationName").asText());
      }
      if (node.has("streetName")) {
        builder.setStreetName(node.get("streetName").asText());
      }
      if (node.has("cityOrTown")) {
        builder.setCity(node.get("cityOrTown").asText());
      }
      if (node.has("provinceOrState")) {
        builder.setProvinceOrState(node.get("provinceOrState").asText());
      }
      if (node.has("country")) {
        builder.setCountry(node.get("country").asText());
      }
      if (node.has("postalCode")) {
        builder.setPostalCode(node.get("postalCode").asText());
      }

      return builder.build();
    }

    return null;
  }
}
