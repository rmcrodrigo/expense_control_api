package com.rodmontiel.ec.contracts.v1.response.expense;

import java.util.Collection;

import com.rodmontiel.ec.contracts.v1.response.BaseResponse;
import com.rodmontiel.ec.dto.ExpenseDTO;

public class GetAllExpensesRs extends BaseResponse {
	public Collection<ExpenseDTO> expenses;
}
