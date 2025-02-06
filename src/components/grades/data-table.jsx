"use client"
import { useState } from "react"
import {
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from "@tanstack/react-table"
import { SlidersHorizontal, PenLine } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { GradesDialog } from "./grades-dialog"

export function DataTable({ columns, data }) {
  const [sorting, setSorting] = useState([])
  const [columnFilters, setColumnFilters] = useState([])
  const [columnVisibility, setColumnVisibility] = useState({})
  const [rowSelection, setRowSelection] = useState({})
  const [isGradesDialogOpen, setIsGradesDialogOpen] = useState(false)

  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    onSortingChange: setSorting,
    getSortedRowModel: getSortedRowModel(),
    onColumnFiltersChange: setColumnFilters,
    getFilteredRowModel: getFilteredRowModel(),
    onColumnVisibilityChange: setColumnVisibility,
    onRowSelectionChange: setRowSelection,
    enableRowSelection: true,
    state: {
      sorting,
      columnFilters,
      columnVisibility,
      rowSelection,
    },
  })

  const handleGradesSubmit = ({ exam1, exam2, final, studentIds }) => {
    console.log("Grades submitted:", { exam1, exam2, final, studentIds })
    setRowSelection({})
    setIsGradesDialogOpen(false)
  }

  return (
    <div className="py-2">
      <div className="flex items-center justify-between py-2">
        <Input
          placeholder="Filter students..."
          value={table.getColumn("name")?.getFilterValue() ?? ""}
          onChange={(event) => table.getColumn("name")?.setFilterValue(event.target.value)}
          className="max-w-sm"
        />
        <div className="flex items-center space-x-2">
          <Button
            variant="outline"
            onClick={() => setIsGradesDialogOpen(true)}
            disabled={Object.keys(rowSelection).length === 0}
            className="gap-2"
          >
            <PenLine className="h-4 w-4" />
            Update Grades
          </Button>
        </div>
      </div>
      <div className="rounded-md border border-zinc-300 dark:border-zinc-700">
        <Table className="[&_tr:hover]:bg-muted/50">
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow
                key={headerGroup.id}
                className="border-b border-zinc-300 dark:border-zinc-700 hover:bg-transparent h-8"
              >
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id} className="h-8 bg-gray-200 px-2 text-xs">
                      {header.isPlaceholder ? null : flexRender(header.column.columnDef.header, header.getContext())}
                    </TableHead>
                  )
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow
                  key={row.id}
                  data-state={row.getIsSelected() && "selected"}
                  className="h-8 border-b border-zinc-300 dark:border-zinc-700"
                >
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id} className="px-2 text-sm">
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-8 text-center">
                  No results.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
      <div className="flex items-center justify-between space-x-2 py-4">
        <div className="text-sm text-muted-foreground">
          {table.getFilteredSelectedRowModel().rows.length} of {table.getFilteredRowModel().rows.length} row(s) selected.
        </div>
        <div className="space-x-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => table.previousPage()}
            disabled={!table.getCanPreviousPage()}
          >
            Previous
          </Button>
          <Button 
            variant="outline" 
            size="sm" 
            onClick={() => table.nextPage()} 
            disabled={!table.getCanNextPage()}
          >
            Next
          </Button>
        </div>
      </div>
      <GradesDialog
        open={isGradesDialogOpen}
        onOpenChange={setIsGradesDialogOpen}
        selectedStudents={Object.keys(rowSelection)}
        onSubmit={handleGradesSubmit}
      />
    </div>
  )
}