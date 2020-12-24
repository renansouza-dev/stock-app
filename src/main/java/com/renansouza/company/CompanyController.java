package com.renansouza.company;

import com.renansouza.base.Constants;
import com.renansouza.base.DefaultController;
import com.renansouza.base.SortingAndOrderArguments;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;

@Validated
@ExecuteOn(TaskExecutors.IO)
@Tag(name = "companies")
@Schema(type = Constants.COMPANIES_ROOT)
@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(value = Constants.COMPANIES_ROOT)
public class CompanyController extends DefaultController {

    @Inject
    CompanyService service;

    @Get(value = "{?args*}" ,produces = MediaType.APPLICATION_JSON)
    @ApiResponse(responseCode = "200", description = "A successful search for companies save.")
    @ApiResponse(responseCode = "500", description = "A failed search for companies save.")
    @Operation(summary = "Search for companies saved.", description = "A list of companies is returned, if any.")
    MutableHttpResponse<?> getCompanies(@Valid SortingAndOrderArguments args) {
        return HttpResponse
                .ok(service.findAll(args))
                .header(HttpHeaders.LOCATION, location(Constants.COMPANIES_ROOT).getPath());
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    MutableHttpResponse<?> getCompany(@PathVariable("id") long id) {
        return HttpResponse
                .ok(service.findById(id))
                .header(HttpHeaders.LOCATION, location(Constants.COMPANIES_ROOT, id).getPath());
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    @ApiResponse(responseCode = "201", description = "Company created.")
    @ApiResponse(responseCode = "400", description = "Invalid company constrain supplied.")
    @ApiResponse(responseCode = "500", description = "Invalid company business rule supplied.")
    @Operation(summary = "Add a new company.", description = "A new company is returned")
    MutableHttpResponse<?> saveCompany(@Parameter(description="The company data.") @Body @Valid Company company) {
        final var savedCompany = service.save(company);

        return HttpResponse
                .created(savedCompany)
                .header(HttpHeaders.LOCATION, location(Constants.COMPANIES_ROOT, savedCompany.getId()).getPath());
    }

    @Put(consumes = MediaType.APPLICATION_JSON)
    @ApiResponse(responseCode = "204", description = "No content.")
    @ApiResponse(responseCode = "400", description = "Invalid company constrain supplied.")
    @ApiResponse(responseCode = "500", description = "Invalid company business rule supplied.")
    @Operation(summary = "Update a company.", description = "Nothing if everything is ok.")
    MutableHttpResponse<?> updateCompany(@Parameter(description="The company data.") @Body @Valid Company company) {
        service.update(company);

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(Constants.COMPANIES_ROOT, company.getId()).getPath());
    }

    @ApiResponse(responseCode = "204", description = "No content.")
    @ApiResponse(responseCode = "400", description = "Invalid company constrain supplied.")
    @ApiResponse(responseCode = "500", description = "Invalid company business rule supplied.")
    @Operation(summary = "Delete a company.", description = "Mark a company as deleted and stop listing the company on standard get companies method.")
    @Delete(value = "/{id}", consumes = MediaType.APPLICATION_JSON)
    MutableHttpResponse<?> deleteCompany(@PathVariable("id") long id) {
        service.delete(id);

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(Constants.COMPANIES_ROOT, id).getPath());
    }

}