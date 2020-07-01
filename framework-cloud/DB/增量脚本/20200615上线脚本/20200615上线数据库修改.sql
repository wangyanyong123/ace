-- 修复历史服务订单确认完成操作错误记录
UPDATE biz_reservation_order_operation_record
SET step_status = 27,
 curr_step = '服务完成',
 description = '服务完成，请您对我们的服务进行评价。'
WHERE
	id IN (
		SELECT
			s.id
		FROM
			(
				SELECT
					r.id
				FROM
					biz_reservation_order_operation_record r
				INNER JOIN (
					SELECT
						r.id,
						r.order_id,
						r.curr_step,
						MAX(r.create_time) create_time
					FROM
						biz_reservation_order_operation_record r
					WHERE
						r.`status` = 1
					AND r.step_status = 18
					GROUP BY
						r.order_id,
						r.step_status
					HAVING
						COUNT(*) > 1
				) s ON r.order_id = s.order_id
				AND r.create_time = s.create_time
				WHERE
					r.`status` = 1
				AND r.step_status = 18
				ORDER BY
					r.order_id,
					r.step_status
			) s
	);
-- 添加确认完成时间
ALTER TABLE biz_reservation_order ADD confirm_time datetime comment '确认完成时间';

-- 修复服务订单确认完成时间
UPDATE biz_reservation_order o,
 (
	SELECT
		o.id,
		r.create_time
	FROM
		biz_reservation_order o
	INNER JOIN biz_reservation_order_operation_record r ON o.id = r.order_id
	AND r.curr_step = '服务完成'
	WHERE
		o.order_status = 35
	AND o.`status` = 1
) p
SET o.confirm_time = p.create_time
WHERE
	o.id = p.id;