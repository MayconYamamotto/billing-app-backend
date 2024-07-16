package br.com.billing.infrastructure.controller;

import br.com.billing.domain.dto.Bill;
import br.com.billing.domain.record.PayInput;
import br.com.billing.domain.record.TotalBillsPeriodInput;
import br.com.billing.infrastructure.pageable.PageResult;
import br.com.billing.infrastructure.pageable.Pagination;
import br.com.billing.domain.record.PayInBulkInput;
import br.com.billing.domain.record.PayInBulkOutput;
import br.com.billing.domain.service.BillService;
import br.com.billing.domain.service.BillingImportHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.com.billing.utils.MessageUtils.ID_REQUIRED;
import static java.util.Objects.requireNonNull;

@Controller
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("app/entities/bill")
public class BillController {

    private final BillService billService;
    private final BillingImportHistoryService billingImportHistoryService;

    @PostMapping
    public ResponseEntity<Bill> create(@Valid @RequestBody Bill bill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.create(bill));
    }

    @PostMapping("/pay")
    public ResponseEntity<Bill> pay(@Valid @RequestBody PayInput input) {
        return ResponseEntity.ok(billService.pay(input.id()));
    }

    @PostMapping("/payInBulk")
    public ResponseEntity<PayInBulkOutput> payInBulk(@Valid @RequestBody PayInBulkInput bills) {
        return ResponseEntity.ok(billingImportHistoryService.processPayInBulk(bills.file()));
    }

//    @PostMapping("/{id}/cancel")
//    public ResponseEntity<Bill> cancel(@PathVariable("id") UUID id) {
//        return ResponseEntity.ok(billService.cancel(id));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> readAsDto(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(billService.readAsDto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> update(@PathVariable UUID id, @Valid @RequestBody Bill bill) {
        requireNonNull(id, ID_REQUIRED);
        requireNonNull(bill, "Tarefa é obrigatória");

        if (!id.equals(bill.getId())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(billService.update(bill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        requireNonNull(id, ID_REQUIRED);
        billService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public PageResult<Bill> list(@RequestBody Pagination pageable) {
        return billService.listAsDTO(pageable.getCustomFilters(), pageable.getPageNumber(), pageable.getPageSize());
    }

    @PostMapping("/totalBillsPeriod")
    public ResponseEntity<?> totalBillsPeriod(@Valid @RequestBody TotalBillsPeriodInput input) {
        return ResponseEntity.ok(billService.totalBillsPeriod(input));
    }
}
